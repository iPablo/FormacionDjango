#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import unicode_literals

from django.db import models


class Base(models.Model):
    title = models.TextField()

    class Meta:
        abstract = True

    def __str__(self):
        return self.title


class BaseNews(Base):
    description = models.TextField()

    def __str__(self):
        return self.description


class NewsItem(BaseNews):
    publish_date = models.DateField()


class Event(BaseNews):
    start_date = models.DateField()
    end_date = models.DateField()
