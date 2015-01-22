package br.eti.clairton.cdi.test;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;

@ConversationScoped
public class ConversationBean extends Bean implements Serializable {

	private static final long serialVersionUID = 1L;

}
