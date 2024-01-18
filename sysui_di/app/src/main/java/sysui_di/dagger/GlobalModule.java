package sysui_di.dagger;

import dagger.Module;


@Module(includes = {
        AndroidInternalsModule.class,
        FrameworkServicesModule.class
})
public class GlobalModule {
    
}
