from rest_framework import serializers
from proyectoinicio.models import NewsItem, Base, BaseNews, Event


class NewsItemSerializer(serializers.ModelSerializer):
  class Meta:
    model = NewsItem
    fields = ('id' , 'title', 'description', 'publish_date')

class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model=Event
        fields = ('id', 'title', 'description', 'start_date' , 'end_date')