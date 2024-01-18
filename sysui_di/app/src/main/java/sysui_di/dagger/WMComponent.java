package sysui_di.dagger;

import dagger.Subcomponent;

@Subcomponent
public interface WMComponent {
    @Subcomponent.Builder
    interface Builder {
        WMComponent build();
    }
}
