package com.annconia.api.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

public class DefaultModule extends SimpleModule {

	public DefaultModule() {
		super("DefaultModule", new Version(1, 0, 0, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		//context.setMixInAnnotations(PageingAndSorting.class, DefaultProfileMixin.class);
	}

}
