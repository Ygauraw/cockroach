package gark.tap.cockroach.units;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class UnitBot {

	private Constructor<?> constructor;
	private Object[] objects;
	private int delay = 1;

	public UnitBot(Constructor<?> constructor, Object[] objects) {
		this.constructor = constructor;
		this.objects = objects;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public Constructor<?> getConstructor() {
		return constructor;
	}

	public Object[] getObjects() {
		return objects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constructor == null) ? 0 : constructor.hashCode());
		result = prime * result + Arrays.hashCode(objects);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnitBot other = (UnitBot) obj;
		if (constructor == null) {
			if (other.constructor != null)
				return false;
		} else if (!constructor.equals(other.constructor))
			return false;
		if (!Arrays.equals(objects, other.objects))
			return false;
		return true;
	}

}
