package br.eti.clairton.cdi.test;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiJUnit4Runner.class)
public class CdiJUnit4RunnerTest {
	private @Inject RequestBean request;
	private @Inject SessionBean session;
	private @Inject ConversationBean conversation;
	private @Inject ApplicationBean application;
	private @Inject SingletonBean singleton;

	@Test
	public void testSingleton() {
		assertNotNull(singleton);
		singleton.run();
	}

	@Test
	public void testApplication() {
		assertNotNull(application);
		application.run();
	}

	@Test
	public void testRequest() {
		assertNotNull(request);
		request.run();
	}

	@Test
	public void testConversation() {
		assertNotNull(conversation);
		conversation.run();
	}

	@Test
	public void testSession() {
		assertNotNull(session);
		session.run();
	}

}
