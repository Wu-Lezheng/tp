package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonCategory;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("/", 2);
        if (nameKeywords.length < 2 || nameKeywords[1].length() == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String query = nameKeywords[1].trim();
        switch (nameKeywords[0]) {
        case "b":
            return new FindCommand(new NameContainsKeywordsPredicate<>(Arrays.asList(query)),
                    new NameContainsKeywordsPredicate<>(Arrays.asList(query)),
                    new NameContainsKeywordsPredicate<>(Arrays.asList(query)),
                    PersonCategory.getFromString("Buyer"));
        case "d":
            return new FindCommand(new NameContainsKeywordsPredicate<>(Arrays.asList(query)),
                    new NameContainsKeywordsPredicate<>(Arrays.asList(query)),
                    new NameContainsKeywordsPredicate<>(Arrays.asList(query)),
                    PersonCategory.getFromString("Deliverer"));
        case "s":
            return new FindCommand(new NameContainsKeywordsPredicate<>(Arrays.asList(query)),
                    new NameContainsKeywordsPredicate<>(Arrays.asList(query)),
                    new NameContainsKeywordsPredicate<>(Arrays.asList(query)),
                    PersonCategory.getFromString("Supplier"));
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
