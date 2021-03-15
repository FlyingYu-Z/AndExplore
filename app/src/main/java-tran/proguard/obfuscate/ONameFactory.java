package proguard.obfuscate;

import java.util.Random;

public class ONameFactory implements NameFactory {
    private static final Random RANDOM = new Random();

    private char c = 'ᐧ';
    private ONameFactory parent = null;
    private boolean mark = RANDOM.nextBoolean();

    ONameFactory() {
    }

    @Override
    public void reset() {
        c = 'ᐧ';
        parent = null;
    }

    @Override
    public String nextName() {
        String name = getName();
        next();
        return name;
    }

    private String getName() {
        return parent == null ? String.valueOf(c) : parent.getName() + c;
    }

    private void next() {
        if (mark)
            switch (c) {
                case 'ˆ':
                    c = 'ᐧ';
                    break;
                case 'ᐧ':
                    c = '　';
                    break;
                case '　':
                    c = 'ˆ';
                    if (parent == null)
                        parent = new ONameFactory();
                    else
                        parent.next();
                    break;
            }
        else
            switch (c) {
                case '　':
                    c = 'ᐧ';
                    break;
                case 'ᐧ':
                    c = 'ˆ';
                    break;
                case 'ˆ':
                    c = '　';
                    if (parent == null)
                        parent = new ONameFactory();
                    else
                        parent.next();
                    break;
            }
    }

}
