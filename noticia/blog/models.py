from __future__ import unicode_literals
from django.utils import timezone
from django.db import models


# Create your models here.


class Base(models.Model):
    class Meta:
        abstract = True
    title = models.CharField(max_length=50)


class BaseNews(Base):
    description = models.TextField(null=True, blank=True)

    def __str__(self):
        return self.title

    def __unicode__(self):
        return self.title


class NewsItem(BaseNews):
    publish_date = models.DateTimeField(blank=False, null=False)

    def publish(self):
            self.published_date = timezone.now()
            self.save()


class Event(BaseNews):
    start_date = models.DateTimeField(blank=False, null=False)
    end_date = models.DateTimeField(blank=False, null=False)
