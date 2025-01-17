/**
 * Pakcage containing REST API controllers.
 */
package com.compagny.v1;
import com.compagny.dto.BillRequest;
import com.compagny.dto.BillResponse;
import com.compagny.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Tag(name = "Bill Controller", description = "Endpoints for discount")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @PostMapping("/calculate")
    @Operation(summary = "Calculate bill with currency", description = "return the net payable amount in the specified target currency after applying applicable discounts and currency conversion ")
    public ResponseEntity<BillResponse> calculateBill(@RequestBody BillRequest request) {
        return ResponseEntity.ok(billService.calculateBill(request));
    }
}