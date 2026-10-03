package dev.chaunm.infrastructure;

import java.util.List;

public record HandlerResult<T>(
        List<Long> responseReceivers,
        Response<T> response
) {
}
