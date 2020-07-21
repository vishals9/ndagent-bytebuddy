package com.cavisson.ndmain;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

public class NDMain {
	public static void premain(String arg, Instrumentation inst) {
		System.out.println("Inside premain......................");
		// new
		// AgentBuilder.Default().ignore(ElementMatchers.nameStartsWith("net.bytebuddy.")).type(ElementMatchers.any())
		// .transform(new NDInstrument()).installOn(inst);

		new AgentBuilder.Default().with(new AgentBuilder.InitializationStrategy.SelfInjection.Eager())
				.type((ElementMatchers.any())).transform((builder, typeDescription, classLoader, module) -> builder
						.method(ElementMatchers.named("callSunHttpClientService")).intercept(Advice.to(TimerAdvice.class)))
				.installOn(inst);

	}
}
