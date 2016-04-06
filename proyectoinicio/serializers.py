from rest_framework import serializers
from proyectoinicio.models import NewsItem, Event
from django.contrib.auth.models import User


class NewsItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = NewsItem
        owner = serializers.ReadOnlyField(source='owner.username')
        fields = ('id', 'title', 'description', 'publish_date', 'owner')


class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event
        owner = serializers.ReadOnlyField(source='owner.username')
        fields = ('id', 'title', 'description', 'start_date', 'end_date', 'owner')


class UserSerializer(serializers.ModelSerializer):
    newsitem = serializers.PrimaryKeyRelatedField(many=True, queryset=NewsItem.objects.all())
    event = serializers.PrimaryKeyRelatedField(many=True, queryset=Event.objects.all())

    class Meta:
        model = User
        fields = ('id', 'username', 'newsitem', 'event')
