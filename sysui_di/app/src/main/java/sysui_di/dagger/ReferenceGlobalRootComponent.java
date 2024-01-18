package sysui_di.dagger;

import javax.inject.Singleton;
import dagger.Component;


@Singleton
@Component(modules = {GlobalModule.class})
public interface ReferenceGlobalRootComponent extends GlobalRootComponent {
    @Component.Builder
    interface Builder extends GlobalRootComponent.Builder {
        ReferenceGlobalRootComponent build();
    }
    ReferenceSysUIComponent.Builder getSysUIComponent();
}
