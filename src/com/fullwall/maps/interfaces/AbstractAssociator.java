package com.fullwall.maps.interfaces;

import java.util.Collection;
import java.util.Set;

import com.fullwall.maps.interfaces.Associatables.Associatable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

/**
 * An abstract implementation of associations. It uses multimaps to maintain two
 * lists of associations - the instanced associations and static class
 * associations.
 * 
 * It defines two abstract methods
 * <code>addAssocation{@link #addAssociation(Object)})</code> and
 * <code>clearAssociations{@link #clearAssociations(Set)}</code>
 * 
 * These are called to extending classes to add an association to their own
 * internal structures.
 * 
 * @author fullwall
 */
public abstract class AbstractAssociator<A> implements Associator<A> {
	private final SetMultimap<Associatable<A>, A> associations = HashMultimap
			.create();
	private final SetMultimap<Class<? extends Associatable<A>>, A> classAssociations = HashMultimap
			.create();

	/**
	 * Called when an association is associated. Extending classes should begin
	 * referencing the object.
	 * 
	 * @param association
	 */
	protected abstract void addAssociation(A association);

	@Override
	public void associate(Associatable<A> associated, A association) {
		this.associations.put(associated, association);
		addAssociation(association);
	}

	@Override
	public void associate(Class<? extends Associatable<A>> associated,
			A association) {
		this.classAssociations.put(associated, association);
		addAssociation(association);
	}

	@Override
	public void clearAssociations() {
		this.associations.clear();
		this.classAssociations.clear();
	}

	/**
	 * Called when an association is removed, and its child objects are removed
	 * from the map. Extending classes should remove references to these child
	 * objects.
	 * 
	 * @param associations
	 */
	protected abstract void clearAssociations(Set<A> associations);

	@Override
	public Collection<A> getAssociations(Associatable<A> association) {
		return this.associations.get(association);
	}

	@Override
	public Collection<A> getAssociations(
			Class<? extends Associatable<A>> association) {
		return this.classAssociations.get(association);
	}

	@Override
	public boolean isAssociated(Associatable<A> association) {
		return this.associations.containsKey(association);
	}

	@Override
	public boolean isAssociated(Class<? extends Associatable<A>> association) {
		return this.classAssociations.containsKey(association);
	}

	@Override
	public void remove(Associatable<A> association) {
		this.clearAssociations(this.associations.removeAll(association));
	}

	@Override
	public void remove(Class<? extends Associatable<A>> association) {
		this.clearAssociations(this.classAssociations.removeAll(association));
	}

}