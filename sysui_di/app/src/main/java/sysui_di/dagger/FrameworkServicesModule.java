package sysui_di.dagger;

import javax.inject.Singleton;

import dagger.Provides;
import dagger.Module;
import sysui_di.Context;
import sysui_di.mockaf.AccessibilityManager;

@Module
public class FrameworkServicesModule {
    // Provides
    @Provides
    @Singleton
    static AccessibilityManager provideAccessibiltyManager(Context context) {
        return new AccessibilityManager();
    }
}
