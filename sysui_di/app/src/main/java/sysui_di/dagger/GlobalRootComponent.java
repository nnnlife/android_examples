package sysui_di.dagger;

import dagger.BindsInstance;

import sysui_di.Context;


public interface GlobalRootComponent {
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        GlobalRootComponent build();
    }

    WMComponent.Builder getWMComponentBuilder();

    SysUIComponent.Builder getSysUIComponent();
}
