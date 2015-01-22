package br.eti.clairton.cdi.test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Instance;

import org.jboss.weld.context.AbstractBoundContext;
import org.jboss.weld.context.BoundContext;
import org.jboss.weld.context.ManagedContext;
import org.jboss.weld.context.beanstore.MapBeanStore;
import org.jboss.weld.context.beanstore.NamingScheme;
import org.jboss.weld.context.beanstore.SimpleNamingScheme;
import org.jboss.weld.context.bound.BoundConversationContext;
import org.jboss.weld.context.bound.BoundRequest;
import org.jboss.weld.context.bound.BoundRequestContext;
import org.jboss.weld.context.bound.BoundSessionContext;
import org.jboss.weld.context.bound.MutableBoundRequest;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;

public class CdiJUnit4Runner extends BlockJUnit4ClassRunner {
	private static Map<String, Object> requestMap = new HashMap<String, Object>();
	private static Map<String, Object> sessionMap = new HashMap<String, Object>();
	private final Class<?> klass;
	private final Weld weld;
	private final WeldContainer container;
	private static final ThreadLocal<MapBeanStore> beanRequestStore = new ThreadLocal<MapBeanStore>() {
		public MapBeanStore get() {
			final NamingScheme namingScheme = new SimpleNamingScheme("");
			return new MapBeanStore(namingScheme, requestMap);
		};
	};
	private static final ThreadLocal<MapBeanStore> beanSessionStore = new ThreadLocal<MapBeanStore>() {
		public MapBeanStore get() {
			final NamingScheme namingScheme = new SimpleNamingScheme("");
			return new MapBeanStore(namingScheme, sessionMap);
		};
	};
	private static final BoundRequest beanConversationStore = new MutableBoundRequest(
			requestMap, sessionMap);

	public CdiJUnit4Runner(final Class<?> klass)
			throws org.junit.runners.model.InitializationError {
		super(klass);
		this.klass = klass;
		this.weld = new Weld();
		this.container = weld.initialize();
		activate(BoundRequestContext.class, beanRequestStore);
		activate(BoundSessionContext.class, beanSessionStore);
		activate(BoundConversationContext.class);
	}

	@Override
	protected Object createTest() throws Exception {
		final Object test = container.instance().select(klass).get();
		return test;
	}

	private <T extends ManagedContext & BoundContext<?>> void activate(
			Class<T> klazz, Object store) {
		final Instance<Object> instance = container.instance();
		final T scope = instance.select(klazz).get();
		try {
			final Field field = AbstractBoundContext.class
					.getDeclaredField("beanStore");
			field.setAccessible(Boolean.TRUE);
			field.set(scope, store);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		scope.activate();
	}

	private <T extends ManagedContext & BoundContext<BoundRequest>> void activate(
			Class<T> klazz) {
		final Instance<Object> instance = container.instance();
		final T scope = instance.select(klazz).get();
		scope.associate(beanConversationStore);
		scope.activate();
	}
}