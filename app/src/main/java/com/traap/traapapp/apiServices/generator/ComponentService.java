package com.traap.traapapp.apiServices.generator;

import dagger.Component;
import com.traap.traapapp.apiServices.di.component.NetComponent;
import com.traap.traapapp.apiServices.scope.CustomScope;

/**
 * Created by Javad.Abadi on 3/26/2018.
 */
@CustomScope
@Component(dependencies = NetComponent.class)
public interface ComponentService {
    void inject(SingletonService singletonService);
}
