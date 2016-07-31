package com.nearor.mylibrary.network;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 *
 * Created by Nearor on 16/7/21.
 */
public class APICallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (Utils.getRawType(returnType) != APICall.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    "MyCall must have generic type (e.g., MyCall<ResponseBody>)");
        }
        final Type responseType = Utils.getSingleParameterUpperBound((ParameterizedType) returnType);
        return new CallAdapter<APICall<?>>() {
            @Override public Type responseType() {
                return responseType;
            }

            @Override public <R> APICall<R> adapt(Call<R> call) {
                return new APICallAdapter<>(call);
            }
        };
    }
}
