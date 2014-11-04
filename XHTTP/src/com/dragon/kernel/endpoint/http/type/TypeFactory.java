package com.dragon.kernel.endpoint.http.type;

import com.dragon.kernel.endpoint.http.type.basic.BooleanType;
import com.dragon.kernel.endpoint.http.type.basic.CharacterType;
import com.dragon.kernel.endpoint.http.type.basic.DoubleType;
import com.dragon.kernel.endpoint.http.type.basic.FloatType;
import com.dragon.kernel.endpoint.http.type.basic.IntType;
import com.dragon.kernel.endpoint.http.type.basic.LongType;
import com.dragon.kernel.endpoint.http.type.basic.StringType;

public final class TypeFactory
{
	private static final TypeFactory	INSTANCE	= new TypeFactory();
	private TypeRegistry				registry;

	private TypeFactory()
	{
		this.registry = TypeRegistry.getInstance();
		init(this.registry);
	}

	public static TypeFactory getInstance()
	{
		return INSTANCE;
	}

	private void init(TypeRegistry typeRegistry)
	{
		typeRegistry.register("int", new IntType());
		typeRegistry.register("java.lang.Integer", new IntType());

		typeRegistry.register("long", new LongType());
		typeRegistry.register("java.lang.Long", new LongType());

		typeRegistry.register("java.lang.String", new StringType());

		typeRegistry.register("boolean", new BooleanType());
		typeRegistry.register("java.lang.Boolean", new BooleanType());

		typeRegistry.register("float", new FloatType());
		typeRegistry.register("java.lang.Float", new FloatType());

		typeRegistry.register("double", new DoubleType());
		typeRegistry.register("java.lang.Double", new DoubleType());

		typeRegistry.register("char", new CharacterType());
		typeRegistry.register("java.lang.Character", new CharacterType());
	}

	public WrapType getType(String classType)
	{
		return this.registry.getType(classType);
	}
}