package fr.thomarz.command.creator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EasyCommandPart {

    private final String name;
    private final String args;
    private final String lore;
    private final IEasyCommand command;

    public String[] getArgs() {
        return args.split(" ");
    }

    public String getInfoLine() {
        return "/" + name + " " + args + ": " + lore;
    }
}
