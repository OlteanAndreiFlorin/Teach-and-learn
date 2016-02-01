package ro.sci.group2.domain;

import org.junit.Assert;
import org.junit.Test;

public class CourseTest {

	private Course subject = new Course();

	@Test()
	public void testSetValidName() {
		subject.setName("Andrei");
		String name = subject.getName();
		Assert.assertEquals("Andrei", name);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidName() {
		subject.setName("Andrei123");
	}

	@Test
	public void testSetValidLevel() {
		subject.setLevel(12);
		int lvl = subject.getLevel();
		Assert.assertEquals(12, lvl);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testsetInvalidLevel() {
		subject.setLevel(25);
	}
}
