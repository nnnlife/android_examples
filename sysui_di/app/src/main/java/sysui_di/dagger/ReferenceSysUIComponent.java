package sysui_di.dagger;


import dagger.Subcomponent;


@Subcomponent
public interface ReferenceSysUIComponent extends SysUIComponent {
    @SysUISingleton
    @Subcomponent.Builder
    interface Builder extends SysUIComponent.Builder {
        ReferenceSysUIComponent build();
    }
}
