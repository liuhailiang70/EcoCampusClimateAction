package generated.carbon;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The carbon and ROI service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: CarbonROIService.proto")
public final class CarbonROIServiceGrpc {

  private CarbonROIServiceGrpc() {}

  public static final String SERVICE_NAME = "carbon.CarbonROIService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<generated.carbon.ConsumptionSample,
      generated.carbon.CarbonReport> getUploadConsumptionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadConsumption",
      requestType = generated.carbon.ConsumptionSample.class,
      responseType = generated.carbon.CarbonReport.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<generated.carbon.ConsumptionSample,
      generated.carbon.CarbonReport> getUploadConsumptionMethod() {
    io.grpc.MethodDescriptor<generated.carbon.ConsumptionSample, generated.carbon.CarbonReport> getUploadConsumptionMethod;
    if ((getUploadConsumptionMethod = CarbonROIServiceGrpc.getUploadConsumptionMethod) == null) {
      synchronized (CarbonROIServiceGrpc.class) {
        if ((getUploadConsumptionMethod = CarbonROIServiceGrpc.getUploadConsumptionMethod) == null) {
          CarbonROIServiceGrpc.getUploadConsumptionMethod = getUploadConsumptionMethod = 
              io.grpc.MethodDescriptor.<generated.carbon.ConsumptionSample, generated.carbon.CarbonReport>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "carbon.CarbonROIService", "UploadConsumption"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.carbon.ConsumptionSample.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.carbon.CarbonReport.getDefaultInstance()))
                  .setSchemaDescriptor(new CarbonROIServiceMethodDescriptorSupplier("UploadConsumption"))
                  .build();
          }
        }
     }
     return getUploadConsumptionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.carbon.RoiRequest,
      generated.carbon.RoiResult> getCalculateROIMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CalculateROI",
      requestType = generated.carbon.RoiRequest.class,
      responseType = generated.carbon.RoiResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.carbon.RoiRequest,
      generated.carbon.RoiResult> getCalculateROIMethod() {
    io.grpc.MethodDescriptor<generated.carbon.RoiRequest, generated.carbon.RoiResult> getCalculateROIMethod;
    if ((getCalculateROIMethod = CarbonROIServiceGrpc.getCalculateROIMethod) == null) {
      synchronized (CarbonROIServiceGrpc.class) {
        if ((getCalculateROIMethod = CarbonROIServiceGrpc.getCalculateROIMethod) == null) {
          CarbonROIServiceGrpc.getCalculateROIMethod = getCalculateROIMethod = 
              io.grpc.MethodDescriptor.<generated.carbon.RoiRequest, generated.carbon.RoiResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "carbon.CarbonROIService", "CalculateROI"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.carbon.RoiRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.carbon.RoiResult.getDefaultInstance()))
                  .setSchemaDescriptor(new CarbonROIServiceMethodDescriptorSupplier("CalculateROI"))
                  .build();
          }
        }
     }
     return getCalculateROIMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CarbonROIServiceStub newStub(io.grpc.Channel channel) {
    return new CarbonROIServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CarbonROIServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CarbonROIServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CarbonROIServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CarbonROIServiceFutureStub(channel);
  }

  /**
   * <pre>
   * The carbon and ROI service definition.
   * </pre>
   */
  public static abstract class CarbonROIServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Client streaming: stream of requests -&gt; one response
     * </pre>
     */
    public io.grpc.stub.StreamObserver<generated.carbon.ConsumptionSample> uploadConsumption(
        io.grpc.stub.StreamObserver<generated.carbon.CarbonReport> responseObserver) {
      return asyncUnimplementedStreamingCall(getUploadConsumptionMethod(), responseObserver);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public void calculateROI(generated.carbon.RoiRequest request,
        io.grpc.stub.StreamObserver<generated.carbon.RoiResult> responseObserver) {
      asyncUnimplementedUnaryCall(getCalculateROIMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUploadConsumptionMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                generated.carbon.ConsumptionSample,
                generated.carbon.CarbonReport>(
                  this, METHODID_UPLOAD_CONSUMPTION)))
          .addMethod(
            getCalculateROIMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.carbon.RoiRequest,
                generated.carbon.RoiResult>(
                  this, METHODID_CALCULATE_ROI)))
          .build();
    }
  }

  /**
   * <pre>
   * The carbon and ROI service definition.
   * </pre>
   */
  public static final class CarbonROIServiceStub extends io.grpc.stub.AbstractStub<CarbonROIServiceStub> {
    private CarbonROIServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CarbonROIServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CarbonROIServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CarbonROIServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Client streaming: stream of requests -&gt; one response
     * </pre>
     */
    public io.grpc.stub.StreamObserver<generated.carbon.ConsumptionSample> uploadConsumption(
        io.grpc.stub.StreamObserver<generated.carbon.CarbonReport> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getUploadConsumptionMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public void calculateROI(generated.carbon.RoiRequest request,
        io.grpc.stub.StreamObserver<generated.carbon.RoiResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCalculateROIMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The carbon and ROI service definition.
   * </pre>
   */
  public static final class CarbonROIServiceBlockingStub extends io.grpc.stub.AbstractStub<CarbonROIServiceBlockingStub> {
    private CarbonROIServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CarbonROIServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CarbonROIServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CarbonROIServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public generated.carbon.RoiResult calculateROI(generated.carbon.RoiRequest request) {
      return blockingUnaryCall(
          getChannel(), getCalculateROIMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The carbon and ROI service definition.
   * </pre>
   */
  public static final class CarbonROIServiceFutureStub extends io.grpc.stub.AbstractStub<CarbonROIServiceFutureStub> {
    private CarbonROIServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CarbonROIServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CarbonROIServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CarbonROIServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary: one request -&gt; one response
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.carbon.RoiResult> calculateROI(
        generated.carbon.RoiRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCalculateROIMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CALCULATE_ROI = 0;
  private static final int METHODID_UPLOAD_CONSUMPTION = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CarbonROIServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CarbonROIServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CALCULATE_ROI:
          serviceImpl.calculateROI((generated.carbon.RoiRequest) request,
              (io.grpc.stub.StreamObserver<generated.carbon.RoiResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_CONSUMPTION:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadConsumption(
              (io.grpc.stub.StreamObserver<generated.carbon.CarbonReport>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CarbonROIServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CarbonROIServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return generated.carbon.CarbonROIServiceImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CarbonROIService");
    }
  }

  private static final class CarbonROIServiceFileDescriptorSupplier
      extends CarbonROIServiceBaseDescriptorSupplier {
    CarbonROIServiceFileDescriptorSupplier() {}
  }

  private static final class CarbonROIServiceMethodDescriptorSupplier
      extends CarbonROIServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CarbonROIServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CarbonROIServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CarbonROIServiceFileDescriptorSupplier())
              .addMethod(getUploadConsumptionMethod())
              .addMethod(getCalculateROIMethod())
              .build();
        }
      }
    }
    return result;
  }
}
