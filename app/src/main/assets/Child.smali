.class public Lfly/sub/Child;
.super Landroid/app/Activity;
.source "Child.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
   value = {
      Lfly/sub/Child$100000000;
   }
.end annotation


# instance fields
.field context:Landroid/content/Context;


# direct methods
.method public constructor <init>()V
   .registers 1

   .prologue
   .line 38
   invoke-direct {p0}, Landroid/app/Activity;-><init>()V

   return-void
.end method


# virtual methods
.method public init()V
   .registers 1
   .annotation system Ldalvik/annotation/Signature;
      value = {
         "()V"
      }
   .end annotation

   .prologue
   .line 14
   iput-object p0, p0, Lfly/sub/Child;->context:Landroid/content/Context;

   return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
   .registers 4
   .annotation system Ldalvik/annotation/Signature;
      value = {
         "(",
         "Landroid/os/Bundle;",
         ")V"
      }
   .end annotation

   .annotation runtime Ljava/lang/Override;
   .end annotation

   .prologue
   .line 20
   invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

   .line 21
   invoke-virtual {p0}, Lfly/sub/Child;->init()V

   .line 23
   new-instance v0, Lfly/sub/Child$100000000;

   invoke-direct {v0, p0}, Lfly/sub/Child$100000000;-><init>(Lfly/sub/Child;)V

   invoke-virtual {v0}, Lfly/sub/Child$100000000;->start()V

   .line 36
   const-string v0, "success"

   const/4 v1, 0x0

   invoke-static {p0, v0, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

   move-result-object v0

   invoke-virtual {v0}, Landroid/widget/Toast;->show()V

   return-void
.end method
