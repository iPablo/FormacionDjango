# -*- coding: utf-8 -*-

from __future__ import unicode_literals
from django.db import models
from django.utils import timezone


class Base(models.Model):
    title = models.CharField(max_length=50)

    class Meta:
        abstract = True

    def __unicode__(self):
        return self.title

    def __str__(self):
        return self.title


class BaseNews(Base):
    description = models.CharField(max_length=100)

    def __unicode__(self):
        return self.description

    def __str__(self):
        return self.description


class NewsItem(BaseNews):
    publish_date = models.DateTimeField(default=timezone.now)

    def __unicode__(self):
        return "Title: " + self.title

    def __str__(self):
        return "Title: " + self.title


class Event(BaseNews):
    start_date = models.DateTimeField(blank=True, null=True)
    end_date = models.DateTimeField(blank=True, null=True)

    def __unicode__(self):
        return "Title: " + self.title

    def __str__(self):
        return "Title: " + self.title
