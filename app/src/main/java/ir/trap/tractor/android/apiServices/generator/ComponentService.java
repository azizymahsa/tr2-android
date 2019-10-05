package ir.trap.tractor.android.apiServices.generator;

import dagger.Component;
import ir.trap.tractor.android.apiServices.di.component.NetComponent;
import ir.trap.tractor.android.apiServices.scope.CustomScope;

/**
 * Created by Javad.Abadi on 3/26/2018.
 */
@CustomScope
@Component(dependencies = NetComponent.class)
public interface ComponentService {
    void inject(SingletonService singletonService);
}
