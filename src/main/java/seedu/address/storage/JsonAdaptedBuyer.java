package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Order;
import seedu.address.model.person.Address;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonCategory;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Buyer}.
 */
class JsonAdaptedBuyer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Buyer's %s field is missing!";

    private final String personCategory;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedOrder> orders = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBuyer} with the given buyer details.
     */
    @JsonCreator
    public JsonAdaptedBuyer(@JsonProperty("personCategory") String personCategory, @JsonProperty("name") String name,
                            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                            @JsonProperty("address") String address,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                            @JsonProperty("orders") List<JsonAdaptedOrder> orders) {
        this.personCategory = personCategory;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (orders != null) {
            this.orders.addAll(orders);
        }
    }

    /**
     * Converts a given {@code Buyer} into this class for Jackson use.
     */
    public JsonAdaptedBuyer(Buyer source) {
        personCategory = source.getPersonCategory().toString();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        orders.addAll(source.getOrderIds().stream()
                .map(JsonAdaptedOrder::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted buyer object into the model's {@code Buyer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted buyer.
     */
    public Buyer toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final ArrayList<Order> modelOrders = new ArrayList<>();
        for (JsonAdaptedOrder order : orders) {
            modelOrders.add(order.toModelType());
        }

        if (personCategory == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!PersonCategory.isValidPersonCategory(personCategory)) {
            throw new IllegalValueException(PersonCategory.MESSAGE_CONSTRAINTS);
        }
        final PersonCategory modelPersonCategory = PersonCategory.getFromString(personCategory);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        return new Buyer(modelPersonCategory, modelName, modelPhone, modelEmail, modelAddress, modelTags, modelOrders);
    }
}
