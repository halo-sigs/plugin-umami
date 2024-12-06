package run.halo.umami;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import run.halo.app.infra.ExternalUrlSupplier;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.feed.TelemetryEventInfo;
import run.halo.feed.TelemetryRecorder;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

@Slf4j
@RequiredArgsConstructor
public class RssTelemetryUmamiRecorder implements TelemetryRecorder {
    private final WebClient webClient = WebClient.builder().build();
    private final ReactiveSettingFetcher settingFetcher;
    private final ExternalUrlSupplier externalUrlSupplier;

    @Override
    public void record(TelemetryEventInfo eventInfo) {
        var propOpt = BasicProp.fetch(settingFetcher).blockOptional();
        if (propOpt.isEmpty()) {
            return;
        }
        var siteUrl = propOpt.get().getEndpoint();
        var webSiteId = propOpt.get().getWebsiteId();
        if (StringUtils.isBlank(siteUrl) || StringUtils.isBlank(webSiteId)) {
            return;
        }
        // https://umami.is/docs/api/sending-stats
        webClient.post()
                .uri(StringUtils.removeEnd(siteUrl, "/") + "/api/send")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.USER_AGENT, genUserAgent())
                .body(Mono.just(createBody(webSiteId, eventInfo)), Map.class)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.debug("Umami send response: {}", response))
                .doOnError(e -> log.debug("Failed to send telemetry event to Umami.", e))
                .block();
    }

    private String genUserAgent() {
        // umami has bot detection to avoid filtering page views
        return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) "
                + "Chrome/131.0.0.0 Safari/537.36";
    }

    private Map<String, Object> createBody(String webSiteId, TelemetryEventInfo eventInfo) {
        Assert.notNull(eventInfo.getTitle(), "Title must not be null.");
        Assert.notNull(eventInfo.getPageUrl(), "Page url must not be null.");

        var hostname = Optional.ofNullable(externalUrlSupplier.getRaw())
                .map(URL::getHost)
                .orElse(StringUtils.EMPTY);
        var payload = new HashMap<>(Map.of(
                "hostname", hostname,
                "language", defaultIfBlank(eventInfo.getLanguageRegion(), Locale.CHINESE.toLanguageTag()),
                "referrer", StringUtils.defaultString(eventInfo.getReferrer()),
                "screen", StringUtils.defaultString(eventInfo.getScreen()),
                "title", eventInfo.getTitle(),
                "url", eventInfo.getPageUrl(),
                "website", webSiteId,
                "tag", "RSS Telemetry"
        ));
        if (StringUtils.isNotBlank(eventInfo.getIp())) {
            payload.put("ip", eventInfo.getIp());
        }
        return Map.of("payload", payload, "type", "event");
    }
}
