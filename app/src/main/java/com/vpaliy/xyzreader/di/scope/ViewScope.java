package com.vpaliy.xyzreader.di.scope;

import java.lang.annotation.Retention;
import javax.inject.Scope;
import java.lang.annotation.RetentionPolicy;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewScope {
}
