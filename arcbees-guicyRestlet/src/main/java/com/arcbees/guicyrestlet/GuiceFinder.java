package com.arcbees.guicyrestlet;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

import com.google.inject.Injector;

/**
 * A finder which uses the specified {@link com.google.inject.Injector} to create resources. The
 * resources are created using <code>injector.getInstance(targetClass)</code>.
 * <p>
 * See {@link RestletServlet} for further
 * details.
 */
public class GuiceFinder extends Finder
{
    protected final Injector injector;


    public GuiceFinder(Injector injector, Context context, Class<? extends ServerResource> targetClass)
    {
        super(context, targetClass);
        this.injector = injector;
    }


    @Override
    public ServerResource create(Class<? extends ServerResource> targetClass, Request request, Response response)
    {
        return injector.getInstance(targetClass);
    }
}
