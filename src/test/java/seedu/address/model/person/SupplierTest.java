package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.BOB;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalDeliverers;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalPets;

public class SupplierTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Supplier supplier = new PersonBuilder().buildSupplier();
        assertThrows(UnsupportedOperationException.class, () -> supplier.getTags().remove(0));
    }

    @Test
    public void addPet_success() {
        Supplier supplier = new PersonBuilder().buildSupplier();
        supplier.addPet(TypicalPets.PLUM);
        assertEquals(supplier.getPetIds(), new ArrayList<>(Arrays.asList(TypicalPets.PLUM)));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Supplier editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).buildSupplier();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).buildSupplier();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Supplier editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).buildSupplier();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).buildSupplier();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Supplier aliceCopy = new PersonBuilder(TypicalPersons.ALICE).buildSupplier();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Supplier editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).buildSupplier();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).buildSupplier();
        assertFalse(TypicalDeliverers.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).buildSupplier();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).buildSupplier();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).buildSupplier();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toString_withPetsOnSale() {
        Supplier supplier1 = new PersonBuilder().buildSupplier();
        Supplier supplier2 = new PersonBuilder().buildSupplier();
        supplier1.addPet(TypicalPets.DOJA);
        supplier1.addPet(TypicalPets.PLUM);
        supplier2.addPet(TypicalPets.DOJA);
        supplier2.addPet(TypicalPets.PLUM);
        assertEquals(supplier1.toString(), supplier2.toString());
    }
}
