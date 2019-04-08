package org.ksql.script.builder;


import org.ksql.script.conversion.TypeConversion;

import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultTypeConversionBuilder implements TypeConversionBuilder{

    private static final ConcurrentHashMap<Class, TypeConversion> cache =
            new ConcurrentHashMap<>();

    @Override
    public <T, S> TypeConversion<T, S> create(T t, S s) {
        if(cache.contains(t.getClass())){
            return (TypeConversion<T, S>) cache.get(t.getClass());
        }
        if(s instanceof ResultSet){
            TypeConversion<T,S> conversion = new VertxTypeConversion<>();
            cache.put(t.getClass(),new VertxTypeConversion());
            return conversion;
        }
        return null;
    }
}
