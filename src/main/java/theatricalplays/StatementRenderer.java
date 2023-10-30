package theatricalplays;

import java.util.Map;

public interface StatementRenderer {
    String render(Invoice invoice, Map<String, Play> plays);
}

