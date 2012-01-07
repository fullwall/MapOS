package com.fullwall.maps.sk89q;

import java.lang.annotation.Annotation;

public interface RequirementHandler {
	void evaluateRequirements(Annotation annotation, Object... arguments)
			throws RequirementMissingException;

	Class<? extends Annotation> getRequirementAnnotation();
}
