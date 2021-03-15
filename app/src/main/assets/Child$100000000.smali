.class Lfly/sub/Child$100000000;
.super Ljava/lang/Thread;
.source "Child.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
   value = Lfly/sub/Child;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
   accessFlags = 0x20
   name = "100000000"
.end annotation


# instance fields
.field private final this$0:Lfly/sub/Child;


# direct methods
.method constructor <init>(Lfly/sub/Child;)V
   .registers 2

   invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

   iput-object p1, p0, Lfly/sub/Child$100000000;->this$0:Lfly/sub/Child;

   return-void
.end method

.method static access$0(Lfly/sub/Child$100000000;)Lfly/sub/Child;
   .registers 2

   iget-object v0, p0, Lfly/sub/Child$100000000;->this$0:Lfly/sub/Child;

   return-object v0
.end method


# virtual methods
.method public run()V
   .registers 1
   .annotation system Ldalvik/annotation/Signature;
      value = {
         "()V"
      }
   .end annotation

   .annotation runtime Ljava/lang/Override;
   .end annotation

   return-void
.end method
