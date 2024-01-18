package sysui_di.dagger;

import dagger.Subcomponent;

@Subcomponent
public interface SysUIComponent {
    @SysUISingleton
    @Subcomponent.Builder
    interface Builder {
        SysUIComponent build();
    }
}
