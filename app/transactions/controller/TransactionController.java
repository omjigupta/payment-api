package transactions.controller;

import com.google.inject.Inject;
import global.common.BaseController;
import global.common.TransactionStatus;
import global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import play.mvc.BodyParser;
import play.mvc.Result;
import transactions.Service.TransactionService;
import transactions.Service.TransactionStateService;
import transactions.models.Transaction;
import transactions.models.TransactionState;
import transactions.request.TransactionRequestDTO;
import transactions.request.TransactionRequestMapper;

import javax.inject.Singleton;

@Singleton
@Slf4j
public class TransactionController extends BaseController {

    private final TransactionRequestMapper transactionRequestMapper;
    private final TransactionService transactionService;
    private final TransactionStateService transactionStateService;

    @Inject
    public TransactionController(TransactionRequestMapper transactionRequestMapper, TransactionService transactionService, TransactionStateService transactionStateService) {
        this.transactionRequestMapper = transactionRequestMapper;
        this.transactionService = transactionService;
        this.transactionStateService = transactionStateService;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result transaction() {
        final TransactionRequestDTO transferRequestDTO = TransactionRequestDTO.getFromRequest(request());
        try {
            final Transaction requestedTransfer = transactionRequestMapper.transform(transferRequestDTO);
            final Transaction finishedTransfer = transactionService.perform(requestedTransfer);
            final TransactionState log = transactionStateService.stateLog(finishedTransfer, TransactionStatus.SUCCESS.name());
            return created(log.asJson());
        } catch (CustomException e) {
            //e.printStackTrace();
            return failure(e.getMessage());
        }
    }

}
