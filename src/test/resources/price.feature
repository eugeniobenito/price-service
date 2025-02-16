Feature: price retrieve

  Scenario Outline: should retrieve prices by product, brand and application datetime
    When the client calls /api/price?applicationDate=<applicationDate>&productId=<productId>&brandId=<brandId> endpoint with GET method
    Then the client receives status code of 200
    And the client receives response body data from file <expectedFile>

    Examples:
      | applicationDate | productId | brandId | expectedFile |
      | 2020-06-14T10:00:00 | 35455 | 1 | expected-price1.json |
      | 2020-06-14T16:00:00 | 35455 | 1 | expected-price2.json |
      | 2020-06-14T21:00:00 | 35455 | 1 | expected-price1.json |
      | 2020-06-15T10:00:00 | 35455 | 1 | expected-price3.json |
      | 2020-06-16T21:00:00 | 35455 | 1 | expected-price4.json |

  Scenario: should return 404 when price not found
    When the client calls /api/price?applicationDate=3020-06-14T10:00:00&productId=0&brandId=0 endpoint with GET method
    Then the client receives status code of 404

  Scenario: should return 400 when invalid parameters are provided
      When the client calls /api/price?product=1 endpoint with GET method
      Then the client receives status code of 400