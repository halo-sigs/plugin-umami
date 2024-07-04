package run.halo.umami;

import org.springframework.stereotype.Component;

import run.halo.app.plugin.BasePlugin;
import run.halo.app.plugin.PluginContext;

/**
 * @author guqing
 * @since 2.0.0
 */
@Component
public class UmamiPlugin extends BasePlugin {

    public UmamiPlugin(PluginContext context) {
        super(context);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}
