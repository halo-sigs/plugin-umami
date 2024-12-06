package run.halo.umami;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import run.halo.app.infra.ExternalUrlSupplier;
import run.halo.app.plugin.ReactiveSettingFetcher;

@Configuration
@ConditionalOnClass(name = "run.halo.feed.TelemetryRecorder")
@RequiredArgsConstructor
public class RssTelemetryConfiguration {
    private final ExternalUrlSupplier externalUrlSupplier;
    private final ReactiveSettingFetcher settingFetcher;

    @Bean
    RssTelemetryUmamiRecorder rssTelemetryUmamiRecorder() {
        return new RssTelemetryUmamiRecorder(settingFetcher, externalUrlSupplier);
    }
}
