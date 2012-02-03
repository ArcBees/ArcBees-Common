package com.arcbees.guicyrestlet;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.ext.servlet.ServletAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract base class for a servlet which acts as a dispatcher for all
 * resources in a Guice based RESTful webapp. Concrete subclasses have to
 * override the method {@link #createRouter(com.google.inject.Injector, Context)} and provide a
 * concrete implementation of {@link GuiceRouter}.
 * <p/>
 * The following code snippets show a typical setup:
 * <p/>
 * <b>web.xml:</b>
 * <p/>
 * <pre>
 * &lt;web-app xmlns="http://java.sun.com/xml/ns/javaee" version="2.5"&gt;
 *     &lt;filter&gt;
 *         &lt;filter-name&gt;guiceFilter&lt;/filter-name&gt;
 *         &lt;filter-class&gt;com.google.inject.servlet.GuiceFilter&lt;/filter-class&gt;
 *     &lt;/filter&gt;
 *     &lt;filter-mapping&gt;
 *         &lt;filter-name&gt;guiceFilter&lt;/filter-name&gt;
 *         &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *     &lt;/filter-mapping&gt;
 *     &lt;listener&gt;
 *         &lt;listener-class&gt;name.pehl.tire.server.servlet.ServletConfig&lt;/listener-class&gt;
 *     &lt;/listener&gt;
 * &lt;/web-app&gt;
 * </pre>
 * <p/>
 * <b>ServletConfig:</b>
 * <p/>
 * <pre>
 * public class ServletConfig extends GuiceServletContextListener
 * {
 *     &#064;Override
 *     protected Injector getInjector()
 *     {
 *         // Further modules are omitted...
 *         return Guice.createInjector(new ServletModule(), new BookstoreModule());
 *     }
 * }
 * </pre>
 * <p/>
 * <b>ServletModule & RestletServlet:</b>
 * <p/>
 * <pre>
 * public class ServletModule extends com.google.inject.servlet.ServletModule
 * {
 *     &#064;Override
 *     protected void configureServlets()
 *     {
 *         serve(&quot;/rest/v1/*&quot;).with(BookstoreRestletServlet.class);
 *     }
 * }
 *
 * &#064;Singleton
 * public class BookstoreRestletServlet extends RestletServlet
 * {
 *     &#064;Override
 *     protected GuiceRouter createRouter(Injector injector, Context context)
 *     {
 *         return new BookstoreRouter(injector, context);
 *     }
 * }
 * </pre>
 * <p/>
 * <b>Router:</b>
 * <p/>
 * <pre>
 * public class BookstoreRouter extends GuiceRouter
 * {
 *     public BookstoreRouter(Injector injector, Context context)
 *     {
 *         super(injector, context);
 *     }
 *
 *
 *     &#064;Override
 *     protected void attachRoutes()
 *     {
 *         attach(&quot;/books&quot;, BooksResource.class);
 *     }
 * }
 * </pre>
 * <p/>
 * <b>Resources:</b>
 * <p/>
 * <pre>
 * public class BookstoreModule extends AbstractModule
 * {
 *     &#064;Override
 *     protected void configure()
 *     {
 *         bind(BookstoreService.class).to(BookstoreServiceImpl.class);
 *         bind(BooksResource.class);
 *     }
 * }
 *
 * public class BooksResource extends ServerResource
 * {
 *     private final BookstoreService service;
 *
 *
 *     &#064;Inject
 *     public ProjectsResource(BookstoreService service)
 *     {
 *         this.service = service;
 *     }
 *
 *
 *     &#064;Get
 *     public List&lt;Book&gt; represent()
 *     {
 *         return service.listBooks();
 *     }
 * }
 * </pre>
 */
@Singleton
public abstract class RestletServlet extends HttpServlet {
    @Inject
    protected Injector injector;
    protected ServletAdapter adapter;


    @Override
    public void init() throws ServletException {
        adapter = new ServletAdapter(getServletContext());
        Context context = adapter.getContext().createChildContext();
        Application application = new Application(context);

        application.setInboundRoot(createRouter(injector, context));
        adapter.setNext(application);
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        adapter.service(request, response);
    }


    protected abstract GuiceRouter createRouter(final Injector injector, final Context context);
}
