package com.tools;

import java.util.Random;

import org.hibernate.Session;

import com.wicket.dao.SessionFactoryUtil;
import com.wicket.model.Contact;

/**
 * Filling DB test-records in amount of COUNT_OF_RECORDS
 */
public class FillingContactBook {

	private final static int COUNT_OF_RECORDS = 10000;

	public static void main(String[] args) {
		long before = System.currentTimeMillis();
		long after;
		long diff;
		Session session = null;

		System.out.println("Adding records...");
		createPerson(session);

		after = System.currentTimeMillis();
		diff = (after - before);
		System.out.println(COUNT_OF_RECORDS + " records added in "
				+ (float) diff / 1000 + " seconds");
	}

	public static void createPerson(Session session) {
		Contact person = new Contact();
		char[] signsSet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };
		int rightLimit = signsSet.length;
		StringBuilder randomName = new StringBuilder("");

		for (int j = 0; j < COUNT_OF_RECORDS; j++) {
			session = SessionFactoryUtil.getSessionFactory()
					.getCurrentSession();
			session.beginTransaction();

			Random random = new Random();
			randomName = new StringBuilder("").append(signsSet[random
					.nextInt(rightLimit)]);
			randomName = new StringBuilder("").append(new String(randomName)
					.toUpperCase());
			for (int i = 0; i < 5; i++)
				randomName.append(signsSet[random.nextInt(rightLimit)]);
			person.setFirstName(randomName.toString());

			randomName = new StringBuilder("").append(signsSet[random
					.nextInt(rightLimit)]);
			randomName = new StringBuilder("").append(new String(randomName)
					.toUpperCase());
			for (int i = 0; i < 5; i++)
				randomName.append(signsSet[random.nextInt(rightLimit)]);
			person.setLastName(randomName.toString());

			randomName = new StringBuilder("");
			for (int i = 0; i < 4; i++)
				randomName.append(signsSet[random.nextInt(rightLimit)]);
			randomName.append("@");
			for (int i = 0; i < 3; i++)
				randomName.append(signsSet[random.nextInt(rightLimit)]);
			randomName.append(".");
			for (int i = 0; i < 2; i++)
				randomName.append(signsSet[random.nextInt(rightLimit)]);
			person.setEmail(randomName.toString());

			person.setNotes("" + (j + 1));
			person.setGroup("Некто");

			session.save(person);
			session.getTransaction().commit();
		}
	}

}
