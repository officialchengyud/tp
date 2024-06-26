package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTORNRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENTNRIC;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.InvalidAppointmentException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Command to add an appointment to MediCLI
 */
public class AddAppointmentCommand extends Command {


    public static final String COMMAND_WORD = "addappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the MediCLI system.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE-TIME "
            + PREFIX_DOCTORNRIC + "DOCTOR NRIC "
            + PREFIX_PATIENTNRIC + "PATIENT NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2024-04-09 10:15 "
            + PREFIX_DOCTORNRIC + "S7777888T "
            + PREFIX_PATIENTNRIC + "T0000111U";

    public static final String MESSAGE_SUCCESS = "New Appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the MediCLI";

    private static final Logger logger = LogsCenter.getLogger(AddAppointmentCommand.class);

    private final Appointment toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toAdd = appointment;
    }

    /**
     * Method that executes command when called by performing checks then adding to the list.
     *
     * @param model the model in which the command is executed
     * @return CommandResult resulting from command execution
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.log(Level.INFO, "going to add appointment");

        if (model.hasAppointment(toAdd)) {
            logger.log(Level.INFO, "appointment was not added as it is in system");
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        try {
            logger.log(Level.INFO, "checking if appointment is valid");
            if (!model.isValidAppointment(toAdd)) {
                logger.log(Level.INFO, "appointment was not added as it is invalid");
                throw new InvalidAppointmentException();
            }
        } catch (PersonNotFoundException e) {
            throw new CommandException("The provided Doctor / Patient is not registered in the system");
        }

        model.addAppointment(toAdd);
        logger.log(Level.INFO, "appointment was added to system");
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }

        AddAppointmentCommand otherAddCommand = (AddAppointmentCommand) other;
        return toAdd.isSameAppointment(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
