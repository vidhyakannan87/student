package com.example.Tuition.service.impl;

import com.example.Tuition.api.request.RazorPaySubscriptionRequest;
import com.example.Tuition.api.request.StudentUpdateRequest;
import com.example.Tuition.model.Student;
import com.example.Tuition.service.RazorPayClientService;
import com.razorpay.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;

import static com.example.Tuition.service.utility.RazorPayConstants.*;
import static com.example.Tuition.service.utility.StripeConstants.*;
import static com.example.Tuition.service.utility.StripeUtility.convertRupeesToPaise;
import static com.example.Tuition.service.utility.UniqueIdGenerator.generateRandomUUID;

@Service
public class RazorPayClientServiceImpl implements RazorPayClientService {

  private RazorpayClient razorpayClient;

  @Value("${razorpay.apiKey}")
  private String razorPayApiKey;

  @Value("${razorpay.apiSecret}")
  private String razorPayApiKeySecret;


  @PostConstruct
  public void init() throws RazorpayException {
    this.razorpayClient = new RazorpayClient(razorPayApiKey, razorPayApiKeySecret);
  }

  @Override
  public Customer createRazorPayCustomer(Student student) throws RazorpayException {
    JSONObject request = new JSONObject();
    request.put(NAME, student.getFirstName() + " " + student.getLastName());
    request.put(EMAIL, student.getEmail());
    request.put(CONTACT, student.getPhoneNumber());

    Customer customer = razorpayClient.Customers.create(request);

    return customer;

  }

  @Override
  public void updateRazorPayCustomer(Student student, StudentUpdateRequest studentUpdateRequest) throws RazorpayException {
    JSONObject request = new JSONObject();
    request.put(NAME, studentUpdateRequest.getFirstName() + " " + studentUpdateRequest.getLastName());
    request.put(EMAIL, studentUpdateRequest.getEmail());
    request.put(CONTACT, studentUpdateRequest.getPhoneNumber());

    razorpayClient.Customers.edit(student.getRazorPayCustomerId(), request);
  }

  @Override
  public Invoice createPaymentLink(Student student, float amount) throws RazorpayException {

    JSONObject lineItem = new JSONObject();
    lineItem.put(AMOUNT, convertRupeesToPaise(amount));
    lineItem.put(NAME, "Tuition Fee Payment by Student Id: " + student.getExternalId());

    JSONArray lineItems = new JSONArray();
    lineItems.put(lineItem);

    JSONObject request = new JSONObject();
    request.put(LINE_ITEMS, lineItems);
    request.put(DESCRIPTION, "Tuition Fee Payment by Student Id: " + student.getExternalId());
    request.put(INVOICE_NUMBER, generateRandomUUID());
    request.put(CUSTOMER_ID, student.getRazorPayCustomerId());
    request.put(DATE, Instant.now().getEpochSecond());
    request.put(CURRENCY, INR);
    request.put(EMAIL_NOTIFY, 1);
    request.put(SMS_NOTIFY, 0);

    return razorpayClient.Invoices.create(request);

  }

  @Override
  public Plan createRazorPayPlan(String period, float amount, int interval) throws RazorpayException {
    JSONObject request = new JSONObject();
    request.put(PERIOD, period);
    request.put(INTERVAL, interval);

    JSONObject item = new JSONObject();
    item.put(NAME, period.toUpperCase() + " Plan for " + amount);
    item.put(DESCRIPTION, period.toUpperCase() + " Plan for " + amount);
    item.put(AMOUNT, convertRupeesToPaise(amount));
    item.put(CURRENCY, INR);
    request.put(ITEM, item);

    Plan plan = razorpayClient.Plans.create(request);

    return plan;
  }

  @Override
  public Subscription createSubscriptionLink(Student student, RazorPaySubscriptionRequest subscriptionRequest) throws RazorpayException {

    Plan plan = createRazorPayPlan(subscriptionRequest.getPeriod(), subscriptionRequest.getAmount(), subscriptionRequest.getInterval());

    JSONArray addonsArray = new JSONArray();

    JSONObject notifyInfo = new JSONObject();
    notifyInfo.put(NOTIFY_EMAIL, student.getEmail());

    if (subscriptionRequest.getUpFrontAmount() > 0 && subscriptionRequest.getName() != null) {
      JSONObject item = new JSONObject();
      item.put(NAME, subscriptionRequest.getName());
      item.put(AMOUNT, subscriptionRequest.getUpFrontAmount());
      item.put(CURRENCY, INR);
      addonsArray.put(item);
    }


    JSONObject request = new JSONObject();
    request.put(PLAN_ID, plan.get(RAZORPAYID).toString());
    request.put(TOTAL_COUNT, subscriptionRequest.getTotalCount());
    request.put(QUANTITY, subscriptionRequest.getQuantity());
    request.put(CUSTOMER_NOTIFY, 1);
    request.put(NOTIFY_INFO, notifyInfo);
    if (addonsArray.length() > 0) {
      request.put(ADDONS, addonsArray);
    }


    Subscription subscription = razorpayClient.Subscriptions.create(request);

    return subscription;
  }
}
