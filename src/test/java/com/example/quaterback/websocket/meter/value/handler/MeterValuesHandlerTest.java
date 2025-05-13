//package com.example.quaterback.websocket.meter.value.handler;
//
//import com.example.quaterback.websocket.meter.value.service.MeterValuesService;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.web.socket.WebSocketSession;
//
//import static org.mockito.Mockito.*;
//
//class MeterValuesHandlerTest {
//
//    private MeterValuesService meterValuesService;
//    private MeterValuesHandler handler;
//
//    @BeforeEach
//    void setUp() {
//        meterValuesService = mock(MeterValuesService.class);
//        handler = new MeterValuesHandler(meterValuesService);
//    }
//
//    @Test
//    void handle_정상적으로_호출되면_updateStationEss_가_호출된다() throws Exception {
//        // given
//        WebSocketSession session = mock(WebSocketSession.class);
//        String sessionId = "test-session-id";
//        when(session.getId()).thenReturn(sessionId);
//
//        String json = """
//        [
//          2,
//          "12345678-meter-id",
//          "MeterValues",
//          {
//            "meterValue": [
//              {
//                "timestamp": "2025-05-03T10:00:00",
//                "sampledValue": [
//                  {
//                    "value": 75,
//                    "measurand": "Energy.Active.Import.Register"
//                  }
//                ]
//              }
//            ]
//          }
//        ]
//        """;
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(json);
//
//        when(meterValuesService.updateStationEss(jsonNode, sessionId)).thenReturn("station-001");
//
//        // when
//        handler.handle(session, jsonNode);
//
//        // then
//        verify(meterValuesService, times(1)).updateStationEss(jsonNode, sessionId);
//    }
//}
