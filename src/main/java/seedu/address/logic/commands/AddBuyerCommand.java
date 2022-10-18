package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.person.Buyer;

/**
 * Adds a buyer to the address book.
 * If this buyer comes with multiple orders (where one order can have a pet as well), these orders and pets
 * will be also added to their respective unique lists.
 */
public class AddBuyerCommand extends AddPersonCommand {

    public static final String COMMAND_WORD = AddPersonCommand.COMMAND_WORD + " c/Buyer";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a buyer to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney"
            + PREFIX_ORDER + "(...Order1 fields)"
            + PREFIX_ORDER + "(...Order2 fields)";

    public static final String MESSAGE_SUCCESS = "New buyer added: %1$s";
    public static final String MESSAGE_DUPLICATE_BUYER = "This buyer already exists in the buyer list";

    private final Buyer toAdd;

    /**
     * Creates an AddBuyerCommand to add the specified {@code Buyer}.
     */
    public AddBuyerCommand(Buyer buyer) {
        requireNonNull(buyer);
        toAdd = buyer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBuyer(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUYER);
        }

        List<Order> orders = toAdd.getOrderIds();
        int numOrdersAdded = orders.size();

        for (Order order : orders) {
            if (model.hasOrder(order)) {
                throw new CommandException(AddOrderCommand.MESSAGE_DUPLICATE_ORDER);
            }
        }

        for (Order order : orders) {
            model.addOrder(order);
        }

        model.addBuyer(toAdd);

        return new CommandResult("\n" //TODO To keep a single MESSAGE_SUCCESS
                + numOrdersAdded + (numOrdersAdded == 1 ? " order" : " orders") + " added\n"
                + String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBuyerCommand // instanceof handles nulls
                && toAdd.equals(((AddBuyerCommand) other).toAdd));
    }
}
