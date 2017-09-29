/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.helloworld.template;

import com.vaadin.data.Binder;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.model.TemplateModel;
import com.vaadin.flow.router.View;
import com.vaadin.ui.Tag;
import com.vaadin.ui.combobox.ComboBox;
import com.vaadin.ui.common.AttachEvent;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.polymertemplate.Id;
import com.vaadin.ui.polymertemplate.PolymerTemplate;

/**
 * The one and only view in the hello world application.
 */
@Tag("hello-world")
@HtmlImport("frontend://components/HelloWorld.html")
public class HelloWorld extends PolymerTemplate<HelloWorld.HelloWorldModel> implements View {
	private static final String EMPTY_NAME_GREETING = "Please enter your name";
	/**
	 * Creates the hello world template.
	 */

	@Id("combo")
	private ComboBox<String> combobox;

	@Id("combo2")
	private ComboBox<String> combobox2;

	@Id("inputId")
	private Element input;

	public class MyBean {
		private String value;

		public MyBean() {

		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	private Binder<MyBean> binder = new Binder<>(MyBean.class);

	public HelloWorld() {
		setId("template");
		getModel().setGreeting(EMPTY_NAME_GREETING);

		getElement().addPropertyChangeListener("userInput", event -> sayHello(event.getValue().toString()));

		MyBean m = new MyBean();
		m.setValue("first combo");

		combobox.setItems("first combo", "hello value", "attach value");
		//
		binder.forField(combobox).bind("value");
		//
		binder.readBean(m);

		combobox2.setItems("second combo", "hello2 value", "attach2 value", "3");
		combobox2.setValue("second combo");
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);


		MyBean m = new MyBean();
		m.setValue("attach value");
		binder.readBean(m);
		combobox2.setValue("attach2 value");


	}

	/**
	 * Model for the template.
	 */
	public interface HelloWorldModel extends TemplateModel {
		/**
		 * Gets user input from corresponding template page.
		 *
		 * @return user input string
		 */
		String getUserInput();

		/**
		 * Sets greeting that is displayed in corresponding template page.
		 *
		 * @param greeting greeting string
		 */
		void setGreeting(String greeting);
	}

	private void sayHello(String nameString) {
		if (nameString == null || nameString.isEmpty()) {
			getModel().setGreeting(EMPTY_NAME_GREETING);
		} else {
			getModel().setGreeting(String.format("Hello %s!", nameString));
		}

		MyBean m = new MyBean();
		m.setValue("hello value");
		binder.readBean(m);
		combobox2.setValue("hello2 value");

	}
}
