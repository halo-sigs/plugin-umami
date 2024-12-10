package run.halo.umami;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.dialect.TemplateHeadProcessor;

@Component
public class UmamiTrackerProcessor implements TemplateHeadProcessor {

    private final ReactiveSettingFetcher settingFetcher;

    public UmamiTrackerProcessor(ReactiveSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    @Override
    public Mono<Void> process(ITemplateContext context, IModel model,
                              IElementModelStructureHandler structureHandler) {
        return BasicProp.fetch(settingFetcher)
                .doOnNext(prop -> {
                    final IModelFactory modelFactory = context.getModelFactory();
                    model.add(modelFactory.createText(trackerScript(prop.getWebsiteId(), prop.getEndpoint(), prop.getScriptName())));
                })
                .then();
    }

    private String trackerScript(String websiteId, String endpoint, String scriptName) {
        return """
                <script async defer data-website-id="%s" src="%s/%s"></script>
                """.formatted(websiteId, endpoint, scriptName);
    }
}
