package run.halo.umami;

import lombok.Data;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;

@Data
public class BasicProp {
    private String websiteId;
    private String endpoint;
    private String scriptName;
    private String url;

    public static Mono<BasicProp> fetch(ReactiveSettingFetcher settingFetcher) {
        return settingFetcher.fetch("basic", BasicProp.class);
    }
}
