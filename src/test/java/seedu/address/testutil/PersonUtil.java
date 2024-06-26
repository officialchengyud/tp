package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.AddDoctorCommand;
import seedu.address.logic.commands.AddPatientCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddPatientCommand(Person person) {
        return AddPatientCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddDoctorCommand(Person person) {
        return AddDoctorCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NRIC + person.getNric().nric + " ");
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_DOB + person.getDoB().dateOfBirth.toString() + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getNric().ifPresent(nric -> sb.append(PREFIX_NRIC).append(nric.nric).append(" "));
        descriptor.getDob().ifPresent(dob -> sb.append(PREFIX_DOB).append(dob.dateOfBirth).append(" "));
        return sb.toString();
    }
}
