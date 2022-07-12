package fairyShop.core;

import fairyShop.common.ConstantMessages;
import fairyShop.common.ExceptionMessages;
import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;
import fairyShop.repositories.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {

    private final Repository<Helper> helperRepository;
    private final Repository<Present> presentRepository;

    public ControllerImpl() {
        this.helperRepository = new HelperRepository();
        this.presentRepository = new PresentRepository();
    }

    @Override
    public String addHelper(String type, String helperName) {
        switch (type) {
            case "Sleepy":
                this.helperRepository.add(new Sleepy(helperName));
                break;
            case "Happy":
                this.helperRepository.add(new Happy(helperName));
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.HELPER_TYPE_DOESNT_EXIST);
        }
        return String.format(ConstantMessages.ADDED_HELPER, type, helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
        Helper helper = helperRepository.findByName(helperName);
        if (helper == null) {
            throw new IllegalArgumentException(ExceptionMessages.HELPER_DOESNT_EXIST);
        } else {
            helper.addInstrument(new InstrumentImpl(power));
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power, helperName);
    }


    @Override
    public String addPresent(String presentName, int energyRequired) {
        this.presentRepository.add(new PresentImpl(presentName, energyRequired));
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        StringBuilder builder = new StringBuilder();
        List<Helper> currHelpers = this.helperRepository
                .getModels()
                .stream()
                .filter(s -> s.getEnergy() > 50)
                .collect(Collectors.toList());
        if (currHelpers.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_HELPER_READY);
        } else {
            Present present = this.presentRepository.findByName(presentName);
            int brokenInstruments = 0;
            for (Helper helper : this.helperRepository.getModels()) {
                for (Instrument instrument : helper.getInstruments()) {
                    if (instrument.isBroken()){
                        brokenInstruments++;
                    }
                }
            }

            for (Helper helper : this.helperRepository.getModels()) {
                if (helper.getEnergy() > 50) {
                    for (Instrument instrument : helper.getInstruments()) {
                        while (helper.canWork() && !instrument.isBroken() && !present.isDone()) {
                            helper.work();
                            instrument.use();
                            present.getCrafted();

                            if (instrument.isBroken()) {
                                brokenInstruments++;
                            }
                            if (present.isDone()) {
                                break;
                            }
                        }
                    }
                }
            }
            if (present.isDone()) {
                builder.append(String.format(ConstantMessages.PRESENT_DONE, presentName, "done"));
            } else {
                builder.append(String.format(ConstantMessages.PRESENT_DONE, presentName, "not done"));
            }
            builder.append(String.format(ConstantMessages.COUNT_BROKEN_INSTRUMENTS, brokenInstruments));
        }
        return builder.toString();
    }

    @Override
    public String report() {
        StringBuilder builder = new StringBuilder();
        long craftedPresents = this.presentRepository.getModels()
                .stream()
                .filter(Present::isDone).count();
        builder.append(String.format("%d presents are done!", craftedPresents)).append(System.lineSeparator());
        builder.append("Helpers info:").append(System.lineSeparator());
        for (Helper helper : this.helperRepository.getModels()) {
            builder.append(String.format("Name: %s", helper.getName())).append(System.lineSeparator());
            builder.append(String.format("Energy: %s", helper.getEnergy())).append(System.lineSeparator());
            int notBrokenInstruments = 0;
            for (Instrument instrument : helper.getInstruments()) {
                if (!instrument.isBroken()) {
                    notBrokenInstruments++;
                }
            }
            builder.append(String.format("Instruments: %d not broken left", notBrokenInstruments)).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
