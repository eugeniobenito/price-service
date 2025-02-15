Feature: price retrieve

  Scenario Outline: should retrieve prices by product, brand and application datetime
    When the client calls /api/price?applicationDate=<applicationDate>&productId=<productId>&brandId=<brandId> endpoint with GET method
    Then the client receives status code of 200
    And the client receives response body data from file <expectedFile>

    Examples:
      | applicationDate | productId | brandId | expectedFile |
      | 2020-06-14T10:00:00 | 35455 | 1 | expected-price1.json |
      | 2020-06-14T16:00:00 | 35455 | 1 | expected-price2.json |