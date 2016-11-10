package cn.minxing.restfulwebservice;

import java.lang.annotation.Annotation;

import javax.annotation.Resource;

public class UrlResource implements Resource {

	@Override
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class type() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthenticationType authenticationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shareable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String mappedName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

}
